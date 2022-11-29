#include "States.h"

WiFiServer server(80); // server listens on Port 80 for incoming HTTP requests

// The state function for UNCONNECTED
// sets up Arduino as Access Point with small web server,
// gets a Network SSID and Password input from user, then exits AP mode and tries to connect
void unconnected() {
  // if arduino has just entered this state, WiFi status is IDLE
  if (WiFi.status() == WL_IDLE_STATUS) {
    // start the WiFi module as AP with iSeat SSID name
    WiFi.beginAP(ISEAT_SSID);
    // if failed, there is hardware problem. loop forever, go no further
    if (WiFi.status() != WL_AP_LISTENING) {
      Serial.println("AP failed");
      while (true);
    }
    server.begin(); // start the HTTP server
    // print SSID and IP address to console
    Serial.print("Created AP, SSID: ");
    Serial.println(WiFi.SSID());
    Serial.print("Config Network using web browser @ http://");
    Serial.println(WiFi.localIP());
  }
  // WiFi status is WL_AP_CONNECTED when a client connects to AP
  else if (WiFi.status() == WL_AP_CONNECTED) {
    // check if there is message from client
    WiFiClient client = server.available();
    if (client && client.connected() && client.available()) {
      // read first line (HTTP REQUEST line with method, URI, version)
      String req = client.readStringUntil('\n');
      Serial.println(req);
      // if this is a GET request from '/network' form with ssid & pass arguments
      if (req.startsWith("GET /network")) {
        String ssid, pass;
        parseArgs(req, ssid, pass); // parse inputs from URI
        Serial.println(ssid);
      
        Serial.println(pass);
        // check for empty SSID, empty pass OK if no security
        if (ssid != "") {
          Serial.println("SSID and PASS non-empty, attempting to connect...");
          responseConnecting(client, ssid, pass); // send the connecting response
          client.stop();
          // at this point, client has entered an SSID and Password to server,
          // assign those values to global _ssid and _pass and change State to
          // CONNECTING to try to connect to Network
          _ssid = ssid;
          _pass = pass;
          state = CONNECTING;
          return; // exit state
        } 
      }
      // send normal input box response for SSID and PASS
      responseInput(client, _ssid, _pass);
      client.stop();
    }
  }
}

// parse the 'ssid' and 'pass' arguments from the GET request URI
void parseArgs(String req, String& ssid, String& pass) {
  int lo = 17, hi = lo;
  while (req[++hi] != '&');
  ssid = req.substring(lo+1, hi);  
  lo = (hi += 5);
  while (req[++hi] != ' ');  
  pass = req.substring(lo+1, hi);
}

// default HTTP response to clients accessing Server on root '/'
// sends page a HTML page with a GET form and text box inputs for a Network SSID and Password for connecting to
void responseInput(WiFiClient& client, const String& ssid, const String& pass) {
  // HTTP reponse header:
  client.println("HTTP/1.1 200 OK\nContent-type:text/html\n");

  // HTTP response contents:
  client.print("<h1>");       // use heading for bigger text size
  client.print("<center>");   // center contents
  client.print("<form action=\"/network\" method=\"GET\">");      // HTML form using GET request
  client.print("<br><br>");   // spacing
  client.print("<label for=\"ssid\">Network SSID: </label><br>"); // text label for network SSID
  // SSID text box input (with a previous value if already set once)
  String ssidTextBox = "<input type=\"text\" id=\"ssid\" name=\"ssid\" value=\"" + ssid + "\">";
  client.print(ssidTextBox);
  client.print("<br><br>");   // spacing
  client.print("<label for=\"pass\">Network Password: </label><br>"); // text label for network Password
  // Password text box input (with a previous value if already set once)
  String passTextBox = "<input type=\"text\" id=\"pass\" name=\"pass\" value=\"" + pass + "\">";
  client.print(passTextBox);
  client.print("<br><br>");   // spacing
  client.print("<input type=\"submit\" value=\"Submit\">"); // button to submit form
  client.print("</form>");    // end of form
  client.print("<br><br>");   // spacing

  // if ssid/pass not empty, means the User already tried to connect to Network and failed,
  // values will be shown in the textboxes (see above), and this error message gets displayed below
  if ((ssid != "") || (pass != ""))
    client.print("* ERROR USING ENTERED SSID/PASS, PLEASE TRY AGAIN *");

  client.print("</center>");  // end of center tag
  client.print("</h1>");      // end of heading tag

  // end HTTP response with empty line:
  client.println();
}

// HTTP response to page '/network',
// prints Network SSID and Password entered in preparation to connect
void responseConnecting(WiFiClient& client, const String& ssid, const String& pass) {
  // HTTP response header:
  client.println("HTTP/1.1 200 OK\nContent-type:text/html\n");
  // HTTP response contents (plain text):
  client.print("Attempting to connect to SSID using password...");
  client.print("<br>SSID: ");
  client.print(ssid);
  client.print("<br>pass: ");
  client.print(pass);
  // end HTTP response with empty line:
  client.println();
}
