class MachineModel{
  final int machineId;
  final String machineName;
  final String machineCode;
  final int? facilityId;

  MachineModel({
    required this.machineId,
    required this.machineName,
    required this.machineCode,
    this.facilityId,
  });

  factory MachineModel.fromJson(Map<String, dynamic>json){
    return MachineModel(
        machineId: json['machine_id'] as int,
        machineName: json['machine_name'] as String,
        machineCode: json['machine_code'] as String,
    );
  }
}