#REF: https://gist.github.com/atinsood/5170757
#Just copied over :) for now

import storm
from storm import log
import json
from tornado import websocket, web, ioloop

json_decode = lambda x: json.loads(x)
json_encode = lambda x: json.dumps(x)

class WebSocketBolt(storm.BasicBolt):
    def initialize(self, conf, context):
        self.ws = create_connection("ws://localhost:8888/ws")
        
    def process(self, tup):
        #Expecting tweet counts
        count = tup.values[0]
        self.ws.send(json_encode(count))
        print json_encode(count)
        
        

WebSocketBolt().run()