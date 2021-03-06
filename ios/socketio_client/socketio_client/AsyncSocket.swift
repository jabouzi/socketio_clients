//
//  AsyncSocket.swift
//  socketio_client
//
//  Created by dev on 1/20/17.
//  Copyright © 2017 Skander Jabouzi. All rights reserved.
//

import UIKit
import Foundation

protocol SocketProtocol {
    var socketio: SocketIO { get };
    var url: String!  { get }
    var port: String!  { get }
    var nameSpace: String!  { get }
}

protocol WebsocketDelegate {
    
    func onResult(type: String, value: String)
}

class AsyncSocket: NSObject, SocketProtocol, SocketIODelegate {
    

    internal var socketio: SocketIO

    
//    var socketio = SocketIO;
    var delegate:WebsocketDelegate?
    var _type: String = "";
    var _value: String = "";
    var url: String!
    var port: String!
    var nameSpace: String!
    
    init(url: String, port: String, nameSpace: String, socketio: SocketIO) {
        self.url = url
        self.port = port
        self.nameSpace = nameSpace
        self.socketio = socketio
        print("Socket.io INIT!");
    }
    
    func connect() {
        //socketio = SocketIO(); 10.5.4.151 192.168.1.14
        socketio.delegate = self;
        print("sel.url ###########");
        print(self.url);
        print("sel.url ###########");
        socketio.connect(toHost: self.url ,onPort: Int(self.port)!, withParams: nil, withNamespace: self.nameSpace);
        print("Socket.io Connecting!");
    }
    
    
    func socketIODidConnect(_ socket: SocketIO!) {
        print("Socket.io Connected!");
        //        socketio.sendEvent("set_pa", withData: "text_swift")
    }
    
    func socketIO(_ socket: SocketIO!, didReceiveMessage packet: SocketIOPacket!) {
        print("1. didReceiveMessage >>> data: %@", packet.data);
    }
    
    func socketIO(_ socket: SocketIO!, didReceiveEvent packet: SocketIOPacket!) {
        print("2. didReceiveEvent >>> data: %@", String(packet.data)!);
        print("### typeOf data: %@", type(of: packet.data));
        let jsonData = packet.data.data(using: .utf8)!
        let json = try? JSONSerialization.jsonObject(with: jsonData, options:.allowFragments) as! [String:AnyObject];
        
        if let name = json!["name"] as? String {
            _type = name;
        }
        if let args = json?["args"] as? [[String : String]] {
            _value = args[0]["value"]!;
        }
        
        self.delegate?.onResult(type: _type, value: _value);
    }
    
    func socketIO(_ socket: SocketIO!, didReceiveJSON packet: SocketIOPacket!) {
        print("3. didReceiveJSON >>> data: %@", packet.data);
    }
    
    @nonobjc func socketIO(_ socket: SocketIO!, onError error: NSError!) {
        if Int32(error!.code) == SocketIOUnauthorized.rawValue {
            print("Not Authorized");
        }
        else {
            print("OnError() %@", error);
        }
    }
    
    func socketIODidDisconnect(_ socket: SocketIO!, disconnectedWithError error: Error!) {
        print("socket.io disconnected. did error occur? %@", error);
    }
    
    func webSocketDidOpen(_ webSocket: SRWebSocket!) {
        print("webSocketDidOpen");
    }
    
    func webSocket(_ webSocket: SRWebSocket!, didFailWithError error: Error!) {
        print("didFailWithError");
    }
    
    func webSocket(_ webSocket: SRWebSocket!, didReceiveMessage message: Any!) {
        print("didReceiveMessage %@", message);
    }
    
    func webSocket(_ webSocket: SRWebSocket!, didCloseWithCode code: Int, reason: String!, wasClean: Bool) {
        print("didCloseWithCode %@ %@", code, reason);
    }
}

