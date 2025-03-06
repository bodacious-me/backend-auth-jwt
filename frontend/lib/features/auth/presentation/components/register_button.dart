import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:gymbroz/constant/const.dart';

class RegisterButton extends StatefulWidget {
  final VoidCallback onTap;
  const RegisterButton({super.key, required this.onTap});

  @override
  State<RegisterButton> createState() => _RegisterButtonState();
}

class _RegisterButtonState extends State<RegisterButton> {
  @override
  Widget build(BuildContext context) {
    return SizedBox(
      
      height: 70,
      width: double.infinity,
     child: ElevatedButton(onPressed: widget.onTap, child: Text('Register', style: GoogleFonts.bebasNeue(color: Colors.black,fontSize: 30),),style: ElevatedButton.styleFrom(
      backgroundColor:primaryColor,
     ),),
    );
  }
}
