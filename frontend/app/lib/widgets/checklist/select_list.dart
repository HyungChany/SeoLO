import 'package:flutter/material.dart';

class SelectList extends StatefulWidget {
  final String title;
  final VoidCallback onTap;

  const SelectList({super.key, required this.title, required this.onTap});

  @override
  State<SelectList> createState() => _SelectListState();
}

class _SelectListState extends State<SelectList> {
  @override
  Widget build(BuildContext context) {
    return InkWell(
      onTap: widget.onTap,
      child: Container(
        height: 60,
        padding: const EdgeInsets.only(left: 30),
        alignment: Alignment.centerLeft,
        child: Text(
          widget.title,
          style: const TextStyle(
            fontWeight: FontWeight.bold,
            fontSize: 16,
          ),
        ),
      ),
    );
  }
}
