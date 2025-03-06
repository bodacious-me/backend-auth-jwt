import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:gymbroz/constant/const.dart';
import 'package:gymbroz/features/auth/presentation/bloc/auth_bloc.dart';
import 'package:gymbroz/features/auth/presentation/bloc/auth_event.dart';
import 'package:gymbroz/features/auth/presentation/bloc/auth_state.dart';
import 'package:gymbroz/features/auth/presentation/components/google_button.dart';
import 'package:gymbroz/features/auth/presentation/components/my_textfield.dart';
import 'package:gymbroz/features/auth/presentation/components/register_button.dart';
import 'package:gymbroz/features/home/home_page.dart';

class SignUpPage extends StatelessWidget {
  SignUpPage({super.key});
  TextEditingController emailController = TextEditingController();
  TextEditingController passwordController = TextEditingController();
  FocusNode emailFocusNode = FocusNode();
  FocusNode passwordFocusNode = FocusNode();

  @override
  Widget build(BuildContext context) {
    return BlocConsumer<AuthBloc, AuthState>(builder: (context, state) {
      print('state of the app: ${state.toString()}');
      if (state is AuthLoadingState) {
        return const Scaffold(
            backgroundColor: backGroundColor,
            body: Center(
              child: SpinKitSpinningLines(
                  color: primaryColor, lineWidth: 2, itemCount: 4),
            ));
      }
      return SignInUi(context);
    }, listener: (context, state) {
      if (state is AuthenticatedState) {
        print(state.userId);
        Navigator.push(
            context, MaterialPageRoute(builder: (context) => HomePage()));
      } else if (state is AuthErrrorState) {
        ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text(state.errorMessage.toString())));
      }
    });
  }

  Widget SignInUi(BuildContext context) {
    return Scaffold(
      body: Column(
        children: [
          Expanded(
            child: Center(
              child: Padding(
                padding: const EdgeInsets.all(10.0),
                child: SingleChildScrollView(
                  child: Column(
                    //  mainAxisAlignment: MainAxisAlignment.center,
                    //   crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Align(
                        alignment: Alignment.centerLeft,
                        child: Padding(
                          padding: const EdgeInsets.only(bottom: .0, left: 15),
                          child: Text("Getting Started",
                              style: GoogleFonts.bebasNeue(
                                  color: boldTextColor, fontSize: 30)),
                        ),
                      ),
                      const Align(
                        alignment: Alignment.centerLeft,
                        child: Padding(
                          padding: EdgeInsets.only(bottom: 15.0, left: 15),
                          child: Text(
                            'Create your account and join the Gymbroz',
                            style:
                                TextStyle(color: greyTextColor, fontSize: 15),
                          ),
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.only(
                            left: 15.0, bottom: 15, right: 15),
                        child: MyTextfield(
                          controller: emailController,
                          focusNode: emailFocusNode,
                          hintText: 'Email',
                          obsecure: false,
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.all(15.0),
                        child: MyTextfield(
                          controller: passwordController,
                          focusNode: passwordFocusNode,
                          hintText: 'Password',
                          obsecure: true,
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.all(15.0),
                        child: RegisterButton(onTap: () {
                          if(emailController.text.isEmpty || passwordController.text.isEmpty){

                          }
                          signUpWithGoogle(emailController.text,
                              passwordController.text, context);
                        }),
                      ),
                      Padding(
                        padding: const EdgeInsets.all(15.0),
                        child: Row(
                          children: [
                            Expanded(
                              child: Container(
                                margin: const EdgeInsets.only(right: 20.0),
                                child: const Divider(
                                  color: greyTextColor,
                                  thickness: 1,
                                ),
                              ),
                            ),
                            const Text(
                              'or Sign up with',
                              style: TextStyle(
                                  color: greyTextColor,
                                  fontSize: 20,
                                  fontWeight: FontWeight.w700),
                            ),
                            Expanded(
                              child: Container(
                                margin: const EdgeInsets.only(left: 20.0),
                                child: const Divider(
                                  color: greyTextColor,
                                  thickness: 1,
                                ),
                              ),
                            ),
                          ],
                        ),
                      ),
                      Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          GoogleButton(),
                          //  AppleButton(),
                        ],
                      ),
                      Row(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          Text(
                            'Already a member?',
                            style: GoogleFonts.bebasNeue(
                                color: lightGreyColor, fontSize: 20),
                          ),
                          GestureDetector(
                            onTap: () {},
                            child: Text(
                              'Sign In',
                              style: GoogleFonts.bebasNeue(
                                  color: primaryColor, fontSize: 20),
                            ),
                          )
                        ],
                      ),
                    ],
                  ),
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }

  void signUpWithGoogle(String email, String password, BuildContext context) {
    context.read<AuthBloc>().add(AuthSignUpWithEmailEvent(email, password));
  }
}
