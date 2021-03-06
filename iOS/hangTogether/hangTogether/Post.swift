//
//  Post.swift
//  hangTogether
//
//  Created by 이미정 on 2017. 10. 5..
//  Copyright © 2017년 이미정. All rights reserved.
//

import Foundation
import ObjectMapper

struct Trip: Mappable {
    var date: Date?
    var places: [[String:Any]] = []
    
    init?(map: Map) {
        date <- (map["date"], CustomDateFormatTransform(formatString: "yyyy-MM-dd'T'HH:mm:ss.SSSZ"))
        places <- map["places"]
    }
    
    init() { }
    
    mutating func mapping(map: Map) {
        date <- (map["date"], CustomDateFormatTransform(formatString: "yyyy-MM-dd'T'HH:mm:ss.SSSZ"))
        places <- map["places"]
    }
}

class TripDate: Mappable {
    private(set) var start: Date!
    private(set) var end: Date!
    
    required init?(map: Map) {
        
    }
    
    func mapping(map: Map) {
        start   <- (map["start"], CustomDateFormatTransform(formatString: "yyyy-MM-dd'T'HH:mm:ss.SSSZ"))
        end     <- (map["end"], CustomDateFormatTransform(formatString: "yyyy-MM-dd'T'HH:mm:ss.SSSZ"))
    }
}

class Post: Mappable {
    private(set) var id: String!
    private(set) var writer: User!
    private(set) var created: Date!
    private(set) var title: String!
    private(set) var content: String?
    //MARK: 프론트엔드단에서 시작일, 종료일 필요하다면 Date형식으로 다시 바꿀 것
//    private(set) var tripDate: [String:Date] = [:]
    private(set) var tripDate: TripDate!
    private(set) var trip: [Trip] = []
    private(set) var recruiting: Bool!
    private(set) var guide: [[String:Any]] = []
    private(set) var volunteer: [String:Any] = [:]
    
    required init?(map: Map) {
        
    }
    
    init() {}
    
    func mapping(map: Map) {
        id          <- map["_id"]
        writer      <- map["writer"]
        created     <- (map["created"], CustomDateFormatTransform(formatString: "yyyy-MM-dd'T'HH:mm:ss.SSSZ"))
        title       <- map["title"]
        content     <- map["content"]
//        tripDate    <- (map["tripDate"], DateTransform())
        trip        <- map["trip"]
        recruiting  <- map["recruiting"]
        guide       <- map["guide"]
        volunteer   <- map["volunteer"]
        tripDate    <- map["tripDate"]
        
        var dateJson:[String:Date] = [:]
        dateJson <- (map["tripDate"], CustomDateFormatTransform(formatString: "yyyy-MM-dd'T'HH:mm:ss.SSSZ"))
        
//        let formatter = DateFormatter.date()
//        if let start = dateJson["start"], let end = dateJson["end"] {
//            tripDate = "\(formatter.string(from: start)) ~ \(formatter.string(from: end))"
//        }
    }
}
