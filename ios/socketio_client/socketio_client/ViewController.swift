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

class ViewController: UIViewController, WebsocketDelegate {
    
    var playerViewController : AVPlayerViewController!;
    let asyncSocket: AsyncSocket = AsyncSocket();
    
    override func viewDidLoad() {
        
        super.viewDidLoad()
        asyncSocket.delegate=self
        playMovie()
        
        let backgroundQueue = DispatchQueue(label: "com.skanderjabouzi.socketio-client.queue", qos: .background, target: nil)
        let block = DispatchWorkItem {
            self.asyncSocket.connect();
        }
        backgroundQueue.async(execute: block)
        // Do any additional setup after loading the view, typically from a nib.
    }
    
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
    
    func onResult(type: String, value: String)
    {
        print ("\(type) : \(value)");
    }


}

