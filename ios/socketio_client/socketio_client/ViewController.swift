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
    
    override func viewDidLoad() {
        super.viewDidLoad()
        playMovie()
        
        let asyncSocket = AsyncSocket();
        
        let backgroundQueue = DispatchQueue(label: "com.skanderjabouzi.socketio-client.queue", qos: .background, target: nil)
        let block = DispatchWorkItem {
            asyncSocket.connect();
        }
        backgroundQueue.async(execute: block)
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
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


}

