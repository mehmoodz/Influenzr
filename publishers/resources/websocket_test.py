from tornado import websocket, web, ioloop
import json

cl = [] # this is for demo purposes, never ever use a global var in production!

class IndexHandler(web.RequestHandler):
    ''' index http normal handler'''
    def get(self):
        self.render("index.html")

class SocketHandler(websocket.WebSocketHandler):
    ''' websocket handler '''
    def open(self):
        ''' ran once an open ws connection is made'''
        if self not in cl:
            cl.append(self)

    def on_close(self):
        ''' on close event, triggered once a connection is closed'''
        if self in cl:
            cl.remove(self)

class ApiHandler(web.RequestHandler):
    ''' http async handler for api side'''
    @web.asynchronous
    def get(self, *args):
        self.finish()
        id = self.get_argument("id")
        value = self.get_argument("value")
        data = {"id": id, "value" : value}
        data = json.dumps(data)
        for c in cl:
            c.write_message(data)

    @web.asynchronous
    def post(self):
        pass

app = web.Application([
    (r'/', IndexHandler),
    (r'/ws', SocketHandler),
    (r'/api', ApiHandler),
])

if __name__ == '__main__':
    app.listen(8888)
    ioloop.IOLoop.instance().start()