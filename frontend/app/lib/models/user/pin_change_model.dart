class PinChangeModel {
  final String newPin;

  PinChangeModel({
    required this.newPin,
  });

  factory PinChangeModel.fromJson(Map<String, dynamic> json) {
    return PinChangeModel(
      newPin: json['new_pin'],
    );
  }

  Map<String, dynamic> toJson() => {
    'new_pin': newPin,
  };
}

