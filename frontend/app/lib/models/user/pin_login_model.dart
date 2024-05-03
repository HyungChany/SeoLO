class PinLoginModel {
  final String pin;

  PinLoginModel({
    required this.pin,
  });

  factory PinLoginModel.fromJson(Map<String, dynamic> json) {
    return PinLoginModel(
      pin: json['pin'],
    );
  }

  Map<String, dynamic> toJson() => {
    'pin': pin,
  };
}

