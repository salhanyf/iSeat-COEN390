
from socket import *

seatPage0 = '<!DOCTYPE html>\
                <body>\
                    <h1>\
                        <center>'

seatPage1 = '               <br>\
                            <br>\
                            <a href=seatOpen>Seat Open</a>\
                            <br>\
                            <a href=seatTaken>Seat Taken</a>\
                        </center>\
                    </h1>\
                </body>'

serverSocket = socket(AF_INET, SOCK_STREAM)
serverSocket.bind(('192.168.2.93', 8080))
serverSocket.listen(1)

print('listening...')
while True:
    sock, addr = serverSocket.accept()

    fullReq = sock.recv(1024).decode().split('\n')
    reqLine = fullReq[0].split(' ')
    print('Addr: ' + addr[0] + ' req: ' + ' '.join(reqLine))

    if reqLine[0] == 'GET':
        
        header = 'HTTP/1.1 200 OK\nContent-Type: text/html\n\n'

        if reqLine[1] == '/':
            print('root page')
            data = seatPage0 + seatPage1

        elif reqLine[1] == '/seatOpen':
            print('seat open')
            data = seatPage0 + 'updated to seat open' + seatPage1

        elif reqLine[1] == '/seatTaken':
            print('seat taken')
            data = seatPage0 + 'updated to seat taken' + seatPage1

        else:
            print('page not found')
            header = 'HTTP/1.1 404 Not Found\n\n'
            data = ''

        print('sending response')
        sock.send((header + data).encode())

    sock.close()

