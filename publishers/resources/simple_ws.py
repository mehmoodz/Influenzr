from tornado import websocket, web, ioloop, autoreload
import json
import sys

new_msg=''
old_msg=''

def send_response():
    print 'msg'
    if new_msg<>old_msg:
        print new_msg

class SocketHandler(websocket.WebSocketHandler):
    ''' websocket handler '''
    def open(self):
        ''' ran once an open ws connection is made'''
        self.send('Opened')
        socket=self
    def send(self,message):
        self.write_message(message)
    def on_close(self):
        ''' on close event, triggered once a connection is closed'''
        self.send('Closed')
        

app = web.Application([
    (r'/ws', SocketHandler),
])



if __name__ == '__main__':
    app.listen(8888)
    autoreload.add_reload_hook(send_response)
    autoreload.start()
    new_msg='boo'
    ioloop.IOLoop.instance().start()
    