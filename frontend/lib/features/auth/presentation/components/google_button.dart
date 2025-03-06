import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:gymbroz/constant/const.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:gymbroz/features/auth/presentation/bloc/auth_bloc.dart';
import 'package:gymbroz/features/auth/presentation/bloc/auth_event.dart';
import 'package:gymbroz/features/auth/presentation/pages/signup_page.dart';

class GoogleButton extends StatefulWidget {

  const GoogleButton({super.key});

  @override
  State<GoogleButton> createState() => _GoogleButtonState();
}

class _GoogleButtonState extends State<GoogleButton> {
  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(15.0),
      child: GestureDetector(
        onTap: () {
          context
              .read<AuthBloc>()
              .add(AuthSignUpWithGoogleEvent());
        },
        child: Container(
          padding: EdgeInsets.all(3),
          decoration: BoxDecoration(
            border: Border.all(color: greyTextColor),
            borderRadius: BorderRadius.circular(10),
          ),
          child: Image.network(
            'https://img.icons8.com/color/144/google-logo.png',
            width: 50,
            height: 50,
          ),
        ),
      ),
    );
  }
}
