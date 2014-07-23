from tornado import websocket, web, ioloop
import json
import storm

cl = [] # this is for demo purposes, never ever use a global var in production!
count=0

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
    
	def push(self,message):
		print message
		self.write_message(message)
		

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

class PublishResultBolt(storm.BasicBolt):
	def process(self,tup):
		countr=tup.values[0]
		print countr
		count=count+countr
		socket.push(count)

socket = SocketHandler
app = web.Application([
    (r'/', IndexHandler),
    (r'/ws', socket),
    (r'/api', ApiHandler),
])

PublishResultBolt().run()

if __name__ == '__main__':
    app.listen(8888)
    ioloop.IOLoop.instance().start()
    