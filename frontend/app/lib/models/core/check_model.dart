class CoreCheckModel {
  final String? lockerUid;
  final int? battery;
  final int? machineId;
  String? taskType;
  String? startTime;
  String? endTime;
  String? precaution;
  final String workerName;
  final String workerTeam;
  final String workerTitle;
  final String facilityName;
  final String machineName;
  final String machineCode;

  CoreCheckModel(
      {this.lockerUid,
      this.battery,
      this.machineId,
      this.taskType,
      this.startTime,
      this.endTime,
      this.precaution,
      required this.workerName,
      required this.workerTeam,
      required this.workerTitle,
      required this.facilityName,
      required this.machineName,
      required this.machineCode});

  Map<String, dynamic> toJson() => {
        'locker_uid': lockerUid,
        'battery_info': battery,
        'machine_id': machineId
      };

  factory CoreCheckModel.fromJson(Map<String, dynamic> json) {
    return CoreCheckModel(
        workerName: json['worker_name'],
        workerTeam: json['worker_team'],
        workerTitle: json['worker_title'],
        facilityName: json['facility_name'],
        machineName: json['machine_name'],
        machineCode: json['machine_number']);
  }
}
