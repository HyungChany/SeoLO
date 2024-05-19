class CoreLockedModel {
  final String? lockerUid;
  final int? battery;
  final int? machineId;

  CoreLockedModel({
    this.lockerUid,
    this.battery,
    this.machineId,
  });

  Map<String, dynamic> toJson() => {
        'locker_uid': lockerUid,
        'battery_info': battery,
        'machine_id': machineId
      };
}
