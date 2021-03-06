//
//  Networking.swift
//  hangTogether
//
//  Created by 이미정 on 2017. 10. 3..
//  Copyright © 2017년 이미정. All rights reserved.
//

import Foundation
import Alamofire

class Networking {
    static func getLanguages() {
        Alamofire.request("\(Config.hostURL)/language").responseJSON { response in
            switch response.result {
                case .success(let response):
                    guard let contents = response as? [String] else { return }
                    NotificationCenter.default.post(name: Notification.Name.getLanguages, object: self, userInfo: ["languages": contents])
                case .failure(let error):
                    print("error")
                    print(error)
            }
        }
    }
    
    static func getMainList() {
        Alamofire.request("\(Config.hostURL)/post?page=0").responseJSON { response in
            switch response.result {
            case .success(let response):
                guard let contents = response as? [[String:Any]] else { return }
                
                var mainList:[Post] = []
                for content in contents {
                    if let post = Post(JSON: content) {
                        mainList.append(post)
                    }
                }
                NotificationCenter.default.post(name: Notification.Name.mainList , object: self, userInfo: ["mainList":mainList])
            case .failure(let error):
                print(error)
            }
        }
    }
    
    static func uploadPost(_ parameters: [String:Any]) {
        Alamofire.request("\(Config.hostURL)/post", method: .post, parameters: parameters, encoding: JSONEncoding.default).responseJSON { response in
            switch response.result {
            case .success(let response):
                //TODO: 400같은 에러 오는거 처리하기!
                NotificationCenter.default.post(name: Notification.Name.uploadPost , object: self, userInfo: ["result":"success"])
            case .failure(let error):
                print(error)
                NotificationCenter.default.post(name: Notification.Name.uploadPost , object: self, userInfo: ["result":"error"])
            }
        }
    }
    
    //NOTE : USER
    static func login(_ parameters: [String:Any]) {
        Alamofire.request("\(Config.hostURL)/user/login", method: .post, parameters: parameters, encoding: JSONEncoding.default).responseJSON { response in
            switch response.result {
            case .success(let response):
                if let user = response as? [String:Any] {
                    NotificationCenter.default.post(name: Notification.Name.login , object: self, userInfo: ["result":"success","user":user])
                }else {
                    NotificationCenter.default.post(name: Notification.Name.login , object: self, userInfo: ["result":"fail"])
                }
            case .failure(let error):
                print("login실패",error)
                NotificationCenter.default.post(name: Notification.Name.login , object: self, userInfo: ["result":"error"])
            }
        }
    }
    
    static func join(_ parameters: [String:Any]) {
        Alamofire.request("\(Config.hostURL)/user", method: .post, parameters: parameters, encoding: JSONEncoding.default).responseJSON { response in
            switch response.result {
            case .success(let response):
                if let user = response as? [String:Any] {
                    NotificationCenter.default.post(name: Notification.Name.join , object: self, userInfo: ["result":"success","user":user])
                }else {
                    NotificationCenter.default.post(name: Notification.Name.join , object: self, userInfo: ["result":"fail"])
                }
            case .failure(let error):
                print("login실패",error)
                NotificationCenter.default.post(name: Notification.Name.join , object: self, userInfo: ["result":"error"])
            }
        }
    }
    
    static func checkUserInfo(email: String?, nickname: String?) {
        var type = 0
        var curl = "\(Config.hostURL)/user/check?"
        if let emailQuery = email, nickname == nil {
            curl += "email=\(emailQuery)"
        }else if let nicknameQuery = nickname, email == nil {
            curl += "nickname=\(nicknameQuery)"
            type = 1
        }else {
            print("중복체크는 이메일/닉네임 중 하나만 할 수 있습니다.")
            return
        }
        
        Alamofire.request(curl).responseJSON { response in
            switch response.result {
            case .success(let data):
                let status = response.response!.statusCode == 200 ? true : false
                guard let message = data as? String else { return }
                NotificationCenter.default.post(name: Notification.Name.joinCheck, object: self, userInfo: ["type": type,"status": status, "message": message])
            case .failure(let error):
                print(error)
            }
        }
    }
}
