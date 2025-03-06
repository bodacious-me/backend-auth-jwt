import 'package:flutter/material.dart';
import 'package:gymbroz/constant/const.dart';

class MyTextfield extends StatefulWidget {
  final bool obsecure;
  final String hintText;
  final TextEditingController controller;
  final FocusNode focusNode;
  const MyTextfield(
      {super.key,
      required this.controller,
      required this.focusNode,
      required this.hintText,
      required this.obsecure});

  @override
  State<MyTextfield> createState() => _MyTextfieldState();
}

class _MyTextfieldState extends State<MyTextfield> {
  @override
  Widget build(BuildContext context) {
    return TextField(
      focusNode: widget.focusNode,
      controller: widget.controller,
      obscureText: widget.obsecure,
      decoration: InputDecoration(
        labelText: widget.hintText,
        
        labelStyle: TextStyle(color: greyTextColor),
        floatingLabelStyle: TextStyle(
          color: lightGreyColor,
          fontSize: 18,
        ),
        floatingLabelBehavior: FloatingLabelBehavior.auto,
        enabledBorder: OutlineInputBorder(
          
          borderRadius: BorderRadius.circular(10),
          borderSide: BorderSide(
            
            color: lightGreyColor,),
        ),
        focusedBorder: OutlineInputBorder(
            borderRadius: BorderRadius.circular(8),
            borderSide:
                BorderSide(color: primaryColor)),
        hintText: widget.hintText,
        hintStyle: TextStyle(color: greyTextColor),
        contentPadding: EdgeInsets.symmetric(
          vertical: 25.0,
          horizontal: 10.0,
        ),
      ),
    );
  }
}
