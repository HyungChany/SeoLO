## MVVM 구조를 활용한 개발

### model
애플리케이션의 데이터와 비즈니스 로직을 담당. 모델은 데이터의 저장, 검색, 수정 등을 처리하며, 데이터베이스, 웹 서비스 호출 등 백엔드와의 통신을 담당.
```
 ┃ ┣ 📂models
 ┃ ┃ ┣ 📂core                             // core logic
 ┃ ┃ ┃ ┣ 📜check_model.dart
 ┃ ┃ ┃ ┣ 📜issue_model.dart
 ┃ ┃ ┃ ┣ 📜locked_model.dart
 ┃ ┃ ┃ ┗ 📜unlock_model.dart
 ┃ ┃ ┣ 📂loto                             // loto 작업 내역 작성
 ┃ ┃ ┃ ┣ 📜checklist_model.dart
 ┃ ┃ ┃ ┣ 📜facility_model.dart
 ┃ ┃ ┃ ┣ 📜machine_model.dart
 ┃ ┃ ┃ ┗ 📜task_templates_model.dart
 ┃ ┃ ┣ 📂main                             // main screen의 news
 ┃ ┃ ┃ ┗ 📜news_model.dart
 ┃ ┃ ┗ 📂user                             // user 관리
 ┃ ┃ ┃ ┣ 📜login_model.dart
 ┃ ┃ ┃ ┣ 📜my_info_model.dart
 ┃ ┃ ┃ ┣ 📜my_tasks_model.dart
 ┃ ┃ ┃ ┣ 📜password_change_model.dart
 ┃ ┃ ┃ ┣ 📜password_check_model.dart
 ┃ ┃ ┃ ┣ 📜pin_change_model.dart
 ┃ ┃ ┃ ┗ 📜pin_login_model.dart
```
### view (screen)
사용자에게 보여지는 UI 부분을 담당.
```
┃ ┣ 📂screens
 ┃ ┃ ┣ 📂bluetooth                           // bluetooth (on/off)
 ┃ ┃ ┃ ┣ 📜bluetooth_off_screen.dart
 ┃ ┃ ┃ ┗ 📜bluetooth_screen.dart
 ┃ ┃ ┣ 📂login                               //로그인 관리 (사번, pin)
 ┃ ┃ ┃ ┣ 📜login_screen.dart
 ┃ ┃ ┃ ┗ 📜pin_login_screen.dart
 ┃ ┃ ┣ 📂lotolock                            // loto 작업 내역 작성
 ┃ ┃ ┃ ┣ 📜checklist_screen.dart
 ┃ ┃ ┃ ┣ 📜day_select_screen.dart
 ┃ ┃ ┃ ┣ 📜facility_select_screen.dart
 ┃ ┃ ┃ ┣ 📜machine_select_screen.dart
 ┃ ┃ ┃ ┣ 📜other_worklist_check_screen.dart
 ┃ ┃ ┃ ┣ 📜result_lock_screen.dart
 ┃ ┃ ┃ ┣ 📜result_unlock_screen.dart
 ┃ ┃ ┃ ┣ 📜task_template_select_screen.dart
 ┃ ┃ ┃ ┣ 📜time_select_screen.dart
 ┃ ┃ ┃ ┗ 📜worklist_check_screen.dart
 ┃ ┃ ┣ 📂loto_process                         // loto 절차
 ┃ ┃ ┃ ┗ 📜loto_process_screen.dart
 ┃ ┃ ┣ 📂main                                 // main 화면
 ┃ ┃ ┃ ┗ 📜main_screen.dart
 ┃ ┃ ┣ 📂profile                              // 프로필 화면
 ┃ ┃ ┃ ┣ 📜change_password_screen.dart
 ┃ ┃ ┃ ┣ 📜change_pin_check_screen.dart
 ┃ ┃ ┃ ┣ 📜change_pin_screen.dart
 ┃ ┃ ┃ ┣ 📜check_password_screen.dart
 ┃ ┃ ┃ ┣ 📜check_pin_screen.dart
 ┃ ┃ ┃ ┗ 📜profile_screen.dart
 ```
 ### viewmodel
  Model과 View 사이의 연결 고리 담당. View로부터 입력을 받아 Model을 업데이트하고, Model의 상태 변화를 View에 반영하기 위한 데이터를 준비
 ```
  ┃ ┣ 📂view_models    
 ┃ ┃ ┣ 📂core                                  // core logic
 ┃ ┃ ┃ ┣ 📜core_check_view_model.dart
 ┃ ┃ ┃ ┣ 📜core_issue_view_model.dart
 ┃ ┃ ┃ ┣ 📜core_locked_view_model.dart
 ┃ ┃ ┃ ┗ 📜core_unlock_view_model.dart
 ┃ ┃ ┣ 📂loto                                 // loto 작업 내역 작성
 ┃ ┃ ┃ ┣ 📜checklist_view_model.dart
 ┃ ┃ ┃ ┣ 📜facility_view_model.dart
 ┃ ┃ ┃ ┣ 📜machine_view_model.dart
 ┃ ┃ ┃ ┗ 📜task_templates_view_model.dart
 ┃ ┃ ┣ 📂main                                 // main news
 ┃ ┃ ┃ ┗ 📜news_view_model.dart
 ┃ ┃ ┗ 📂user                                 // user 관리
 ┃ ┃ ┃ ┣ 📜login_view_model.dart
 ┃ ┃ ┃ ┣ 📜logout_view_model.dart
 ┃ ┃ ┃ ┣ 📜my_info_view_model.dart
 ┃ ┃ ┃ ┣ 📜my_tasks_view_model.dart
 ┃ ┃ ┃ ┣ 📜password_change_view_model.dart
 ┃ ┃ ┃ ┣ 📜password_check_view_model.dart
 ┃ ┃ ┃ ┣ 📜pin_change_view_model.dart
 ┃ ┃ ┃ ┗ 📜pin_login_view_model.dart
 ```
 ### service (api)
 ```
  ┃ ┣ 📂services
 ┃ ┃ ┣ 📜core_service.dart                   // core logic
 ┃ ┃ ┣ 📜loto_service.dart                   // loto 작업내역
 ┃ ┃ ┣ 📜news_service.dart                   // main news
 ┃ ┃ ┗ 📜user_service.dart                   // user관리
 ```
 ### 사용된 image와 font

```
┣ 📂assets
 ┃ ┣ 📂fonts
 ┃ ┃ ┣ 📜font_bold.ttf
 ┃ ┃ ┣ 📜font_extrabold.ttf
 ┃ ┃ ┣ 📜font_light.ttf
 ┃ ┃ ┗ 📜font_medium.ttf
 ┃ ┣ 📂images
 ┃ ┃ ┣ 📜bluetooth_icon.png
 ┃ ┃ ┣ 📜clean_icon.png
 ┃ ┃ ┣ 📜clearlock.png
 ┃ ┃ ┣ 📜clearlock2.png
 ┃ ┃ ┣ 📜etc_icon.png
 ┃ ┃ ┣ 📜fail_loto.png
 ┃ ┃ ┣ 📜home_icon.png
 ┃ ┃ ┣ 📜loading_icon.gif
 ┃ ┃ ┣ 📜loading_icon.png
 ┃ ┃ ┣ 📜login.png
 ┃ ┃ ┣ 📜login_logo.png
 ┃ ┃ ┣ 📜loto_lock_icon.png
 ┃ ┃ ┣ 📜loto_process_character.png
 ┃ ┃ ┣ 📜loto_process_icon.png
 ┃ ┃ ┣ 📜maintenance_icon.png
 ┃ ┃ ┣ 📜nfc_icon.png
 ┃ ┃ ┣ 📜nfc_tag_icon.png
 ┃ ┃ ┣ 📜profile_icon.png
 ┃ ┃ ┣ 📜repair_icon.png
 ┃ ┃ ┣ 📜seolo_character.png
 ┃ ┃ ┣ 📜seolo_icon.png
 ┃ ┃ ┣ 📜success_loto.png
 ┃ ┃ ┗ 📜work_report_icon.png
 ┃ ┗ 📂splash
 ┃ ┃ ┗ 📜icon.png
 ```

 ## bluetooth (BLE)

사용한 package
```
flutter_blue_plus: ^1.32.5
```

bluetooth 활성화 감지
```
bluetooth screen의 initState시 bluetooth 상태를 감지

    _adapterStateStateSubscription =
        FlutterBluePlus.adapterState.listen((state) {
      _adapterState = state;
      // bluetooth가 활성화되지 않아있다면 bluetooth off screen으로 이동
      if (_adapterState != BluetoothAdapterState.on) {
        Navigator.pushReplacementNamed(context, '/bluetoothOff');
      }
      if (mounted) {
        setState(() {});
      }
    });
```
initState시 최근 연결한 자물쇠 list를 띄움으로써 편의성 제공
```
    _lastScanResults = FlutterBluePlus.lastScanResults;

    List<Widget> _buildLastScanResultTiles(BuildContext context) {
    return _lastScanResults
        .map(
          (r) => ScanResultTile(
            result: r,
            onTap: () => connectToDevice(r.device),
          ),
        )
        .toList();
  }
```
bluetooth connect
```
  Future<void> connectToDevice(BluetoothDevice device) async {
    await device.connect();
    FlutterBluePlus.stopScan();
    await writeToDevice(device); // 연결 되었다면 바로 데이터 송신 (write)
  }
```
button click을 통해 데이터를 보낼 service와 character를 따로 지정하지 않고, 자동으로 character에 데이터를 송신하도록 지정
```

    await device.discoverServices().then((services) async {
      for (var service in services) {
        if (service.uuid.toString().toUpperCase() ==
            "자물쇠 service uuid") {
          List<BluetoothCharacteristic> characteristics =
              service.characteristics;
          for (var characteristic in characteristics) {
            if (characteristic.uuid.toString().toUpperCase() ==
                "데이터를 송신할 character uuid") {
              bluetoothCharacteristic = characteristic;
```
utf-8 형식으로 데이터 송·수신
데이터들은 flutter_secure_storage를 활용하여 관리
```
String message ="${companyCode ?? ''},${lockerToken ?? ''},${machineId ?? ''},${userId ?? ''},${lockerUid ?? ''},${coreCode ?? 'INIT'}";

List<int> encodedMessage = utf8.encode(message);

try {
    await characteristic.write(encodedMessage, allowLongWrite: true, timeout: 30);
    characteristic.setNotifyValue(true);
    characteristic.read();
    characteristic.lastValueStream.listen((value) async {
        String receivedString = utf8.decode(value);
        _receivedValues = receivedString.split(',');

```
응답값에서 core code에 따라 서로 다른 로직 수행 <br>
ex) write의 경우 작업 내역을 작성하지 않은 상태이므로 작업 내역을 작성하도록 screen 이동 + 작업 내역 확인 screen에서 issue api 요청<br>
writed의 경우 작업 내역을 작성한 상태이므로 issue api 요청 후 성공시 result
```
if (_receivedValues[0] == 'WRITE') {
    Navigator.pushReplacementNamed(context, '/checklist');
}

if (_receivedValues[0] == 'WRITED' &&
   !hasExecutedCoreIssued) {
    hasExecutedCoreIssued = true;
    issueVM.coreIssue().then((_) {
    if (issueVM.errorMessage == null) {
        writeToDevice(device);
    } else {
        showDialog(
        context: context,
        builder: (BuildContext context) {
        return CommonDialog(
            content: issueVM.errorMessage!,
            buttonText: '확인',
        );
    });
    }
});
}
```
데이터 전송 후 수신 전까지 _isWriting을 true로 설정하여 gif 띄움
```
(_isWriting == true)
    ? Center(
        child: Image.asset(
        'assets/images/loading_icon.gif',
        width: 200,
        height: 200,
    ))
    : scan list
```


## pin login
pin login package가 있지만 ui/ux를 위해 직접 구현
