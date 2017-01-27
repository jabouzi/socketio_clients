//
//  ViewController.swift
//  socketio_client
//
//  Created by dev on 1/20/17.
//  Copyright © 2017 Skander Jabouzi. All rights reserved.
//

import UIKit
import AVKit
import AVFoundation

class ViewController: UIViewController {
    
    var playerViewController : AVPlayerViewController!;
    //var socketio = SocketIO();
    
    override func viewDidLoad() {
        
        super.viewDidLoad()
        //connect()
        playMovie()
        
        let asyncSocket = AsyncSocket();
        asyncSocket.connect();
        
        /*let backgroundQueue = DispatchQueue(label: "com.skanderjabouzi.socketio-client.queue", qos: .background, target: nil)
        let block = DispatchWorkItem {
            asyncSocket.connect();
        }
        backgroundQueue.async(execute: block)*/
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    /*func connect() {
        //socketio = SocketIO(); 10.5.4.151 192.168.1.14
        socketio.delegate = self;
        socketio.connect(toHost: "10.5.4.151", onPort: 6543, withParams: nil, withNamespace: "/pa");
    }
    
    
    func socketIODidConnect(_ socket: SocketIO!) {
        print("Socket.io Connected!");
        socketio.sendEvent("set_pa", withData: "text_swift")
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
            print(name)
        }
        if let args = json?["args"] as? [[String : String]] {
            print(args[0]["value"]!);
        }
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

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }*/
    
    func playMovie()
    {
        let view = self.view;
        let videoURL = NSURL(string: "https://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4")
        let player = AVPlayer(url: videoURL! as URL)
        playerViewController = AVPlayerViewController()
        playerViewController.player = player
        resizePlayerToViewSize()
        view?.addSubview(playerViewController.view)
        view?.autoresizesSubviews = true
        self.present(playerViewController, animated: true) {
            self.playerViewController.player!.play()
        }
    }
    
    func resizePlayerToViewSize()
    {
        let frame = self.view.frame;
        playerViewController.view.frame = frame;
    }


}

