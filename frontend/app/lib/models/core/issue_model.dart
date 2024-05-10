class CoreIssueModel {
  final String? lockerUid;
  final int? machineId;
  final int? taskTemplateId;
  final String? taskPrecaution;
  final String? endTime;
  final String? facilityName;
  final String? machineName;
  final String? manager;
  final String? taskTemplateName;
  final String? endDay;
  final String? nextCode;
  final String? tokenValue;

  CoreIssueModel({
    this.lockerUid,
    this.machineId,
    this.taskTemplateId,
    this.taskPrecaution,
    this.endTime,
    this.facilityName,
    this.machineName,
    this.manager,
    this.taskTemplateName,
    this.endDay,
    this.nextCode,
    this.tokenValue,
  });

  Map<String, dynamic> toJson() => {
    'locker_uid': lockerUid,
    'machine_id': machineId,
    'task_template_id': taskTemplateId,
    'task_precaution': taskPrecaution,
    'end_time': endTime,
  };

  factory CoreIssueModel.fromJson(Map<String, dynamic> json) {
    return CoreIssueModel(
      nextCode: json['next_code'],
      tokenValue: json['tokenValue'],
    );
  }
}

