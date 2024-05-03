class PinChangeModel {
  final String newPin;
  final String checkNewPin;

  PinChangeModel({
    required this.newPin,
    required this.checkNewPin
  });

  Map<String, dynamic> toJson() => {
    'new_pin': newPin,
  };
}

