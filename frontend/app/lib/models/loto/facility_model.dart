class FacilityModel {
  final int facilityId;
  final String facilityName;

  FacilityModel({
    required this.facilityId,
    required this.facilityName,
  });

  factory FacilityModel.fromJson(Map<String, dynamic> json) {
    return FacilityModel(
      facilityId: json['id'] as int,
      facilityName: json['name'] as String,
    );
  }
}
