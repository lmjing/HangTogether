//
//  WritePostViewController.swift
//  hangTogether
//
//  Created by 이미정 on 2017. 10. 6..
//  Copyright © 2017년 이미정. All rights reserved.
//

import UIKit

/*
 TODO: 스크롤 뷰 안에 테이블 뷰가 들어있는 안 좋은 형태의 View다. 배민때 처럼 전체를 테이블 뷰로 변경해 새로 시도 할 것
 why? 테이블 뷰 안에 콜렉션뷰가 있기 때문에 높이가 가변적이며 높이를 정확히 알 수 없어 테이블 뷰의 높이를 다시 재 설정해야하는 문제가 발생한다. -> 즉, 높이 재 설정을 막고 자동으로 측정할 수 있게 한다.
 */
/*
 TODO: 실제 장소명 적용하여 보고 너무 길어 범위를 벗어나는 경우 혹은 애매하게 길어서 왼쪽 정렬이 안되는지 확인하고
 너무 길다면 View 모양을 변경해보고 괜찮으면 왼쪽 정렬 시키기
*/
class WritePostViewController: UIViewController {
    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var titleTextField: UITextField!
    //TODO: 논의 후 placehorder 있는 라이브러리 사용하기
    @IBOutlet weak var contentTextView: UITextView!
    @IBOutlet weak var addPlaceButton: UIButton!
    @IBOutlet weak var startDateTextField: UITextField!
    @IBOutlet weak var endDateTextField: UITextField!
    
    let datePicker = UIDatePicker()
    var post:[String:Any] = [:]
    var tripList:[[String:Any]] = []
    var tripDate: [String:String] = [:]
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        titleTextField.delegate = self
        contentTextView.delegate = self
        
        tableView.delegate = self
        tableView.dataSource = self
        
        contentTextView.contentInset = UIEdgeInsetsMake(10, 10, 10, 10)
        
        initView()
        addPlaceButton.addTarget(self, action: #selector(moveAddTripView), for: .touchUpInside)
        NotificationCenter.default.addObserver(self, selector: #selector(finishUpload), name: Notification.Name.uploadPost, object: nil)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        tripList.sort(by: {
            if let date1 = $0["date"] as? String, let date2 = $1["date"] as? String {
                return date1 < date2
            }else {
                return $0["date"] == nil ? false : true
            }
        })
        tableView.reloadData()
    }
    
    func initView() {
        navigationItem.title = "글 작성"
        
        let okButton = UIBarButtonItem(image: #imageLiteral(resourceName: "check"), style: .done, target: self, action: #selector(writeDone))
        navigationItem.setRightBarButton(okButton, animated: true)
        
        contentTextView.drawLine()
        
        datePicker.withTextField(startDateTextField, selector: #selector(pickerDone))
        datePicker.withTextField(endDateTextField, selector: #selector(pickerDone))
        
        let button = UIBarButtonItem(barButtonSystemItem: .cancel, target: self, action: #selector(dismissView))
        navigationItem.leftBarButtonItem = button
    }
    
    func dismissView() {
        dismiss(animated: true, completion: nil)
    }
    
    func finishUpload(notification: Notification) {
        guard let userInfo =  notification.userInfo as? [String:String] else { return }
        switch userInfo["result"] as String! {
        case "success":
            dismiss(animated: true, completion: nil)
        case "error":
            let alert = UIAlertController.okAlert(title: "글 작성 실패", message: "문제가 생겨 글 작성에 실패했습니다.")
            present(alert, animated: true, completion: { self.dismiss(animated: true, completion: nil) })
        default:
            return
        }
    }
    func pickerDone(button: UIBarButtonItem) {
        switch button.tag {
        case 1:
            startDateTextField.text = datePicker.date.string
            tripDate["start"] = datePicker.date.string
        case 2:
            endDateTextField.text = datePicker.date.string
            tripDate["end"] = datePicker.date.string
        default:
            print("error: datePickerDone")
            break
        }
        self.view.endEditing(true)
    }
    
    func writeDone(button: UIBarButtonItem) {
        guard let title = titleTextField.text, !title.isEmpty else {
            let alert = UIAlertController.okAlert(title: nil, message: "제목을 입력해주세요.")
            self.present(alert, animated: true, completion: nil); return
        }
        if tripDate["start"] == nil || tripDate["end"] == nil {
            let alert = UIAlertController.okAlert(title: nil, message: "여행 기간을 입력해주세요.")
            self.present(alert, animated: true, completion: nil); return
        }
        if tripList.count < 1 {
            let alert = UIAlertController.okAlert(title: nil, message: "여행 장소를 하나 이상 등록해주세요.")
            self.present(alert, animated: true, completion: nil); return
        }
        post["tripDate"] = tripDate
        post["title"] = title
        post["content"] = contentTextView.text
        post["trip"] = tripList
        post["writer"] = "59d4f8155bff9515ba6b78df"
        Networking.uploadPost(post)
    }
    
    func moveAddTripView(button: UIButton) {
        if let min = tripDate["start"], let max = tripDate["end"] {
            let addPlaceViewController = UIStoryboard.addPlaceStoryboard.instantiateViewController(withIdentifier: "addPlace") as! AddPlaceViewController
            addPlaceViewController.datePicker.minimumDate = min.date
            addPlaceViewController.datePicker.maximumDate = max.date
            navigationController?.pushViewController(addPlaceViewController, animated: true)
        }else {
            let alert = UIAlertController.okAlert(title: nil, message: "여행 기간을 입력해주세요.")
            self.present(alert, animated: true, completion: nil)
        }
    }
}

extension WritePostViewController: UITextFieldDelegate, UITextViewDelegate {
    func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        guard let text = textField.text else { return true }
        let newLength = text.characters.count + string.characters.count - range.length
        return newLength <= 20
    }
    
    func textView(_ textView: UITextView, shouldChangeTextIn range: NSRange, replacementText text: String) -> Bool {
        guard let str = textView.text else { return true }
        let newLength = str.characters.count + text.characters.count - range.length
        return newLength <= 200
    }
}

extension WritePostViewController: UITableViewDelegate, UITableViewDataSource {
    func numberOfSections(in tableView: UITableView) -> Int {
        return tripList.count
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        guard let places = tripList[section]["places"] as? [[String:Any]] else { return 0 }
        return places.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "tripDateCell", for: indexPath) as! TripTableViewCell
        let trip = tripList[indexPath.section]
        // 첫번째일 경우 날짜 라벨 생성
        if indexPath.row == 0 {
            cell.makeFirstView(date: trip["date"] as? String)
        }
        
        let lastSection = tableView.numberOfSections - 1
        let lastIndexPath = IndexPath(row: tableView.numberOfRows(inSection: lastSection) - 1, section: lastSection)
        cell.makeLine(index: indexPath, count: lastIndexPath)
        if let places = trip["places"] as? [[String:Any]], let name = places[indexPath.row]["name"] as? String {
            cell.placeLabel.text = name
        }
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 35.0
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        guard var places = tripList[indexPath.section]["places"] as? [[String:Any]],
        let name = places[indexPath.row]["name"] as? String else { return }
        let dialog = UIAlertController.cancleOkAlert(title: name, message: "일정에서 삭제하시겠습니까?") { _ in
            places.remove(at: indexPath.row)
            self.tripList[indexPath.section]["places"] = places
            if places.isEmpty {
                self.tripList.remove(at: indexPath.section)
            }
            tableView.reloadData()
        }
        self.present(dialog, animated: true, completion: nil)
    }
}
