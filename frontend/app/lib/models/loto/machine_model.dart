class MachineModel{
  final int machineId;
  final String machineName;

  MachineModel({
    required this.machineId,
    required this.machineName
  });

  factory MachineModel.fromJson(Map<String, dynamic>json){
    return MachineModel(
        machineId: json['machine_id'] as int,
        machineName: json['machine_name'] as String
    );
  }
}