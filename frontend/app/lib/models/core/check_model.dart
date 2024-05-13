class CoreCheckModel {
  final String? lockerUid;
  final int? battery;
  final int? machineId;

  CoreCheckModel({
    this.lockerUid,
    this.battery,
    this.machineId,
  });

  Map<String, dynamic> toJson() => {
    'locker_uid': lockerUid,
    'battery_info': battery,
    'machine_id': machineId
  };

  // factory CoreIssueModel.fromJson(Map<String, dynamic> json) {
  //   return CoreIssueModel(
  //     nextCode: json['next_code'],
  //     tokenValue: json['tokenValue'],
  //   );
  // }
}

