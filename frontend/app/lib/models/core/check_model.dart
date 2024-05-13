class CoreCheckModel {
  final String? lockerUid;
  final int? battery;
  final int? machineId;
  final String? taskType;
  final String? startTime;
  final String? endTime;
  final String? precaution;
  final String? workerName;
  final String? workerTeam;
  final String? workerTitle;
  final String? facilityName;
  final String? machineName;
  final String? machineCode;

  CoreCheckModel(
      {this.lockerUid,
      this.battery,
      this.machineId,
      this.taskType,
      this.startTime,
      this.endTime,
      this.precaution,
      this.workerName,
      this.workerTeam,
      this.workerTitle,
      this.facilityName,
      this.machineName,
      this.machineCode});

  Map<String, dynamic> toJson() => {
        'locker_uid': lockerUid,
        'battery_info': battery,
        'machine_id': machineId
      };

  factory CoreCheckModel.fromJson(Map<String, dynamic> json) {

    return CoreCheckModel(
        taskType: json['task_type'],
        startTime: json['taskStartDateTime'],
        endTime: json['taskEndEstimatedDateTime'],
        precaution: json['taskPrecaution'],
        workerName: json['worker_name'],
        workerTeam: json['worker_team'],
        workerTitle: json['worker_title'],
        facilityName: json['facility_name'],
        machineName: json['machine_name'],
        machineCode: json['machine_number']);
  }
}
