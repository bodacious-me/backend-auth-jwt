import 'package:google_sign_in/google_sign_in.dart';
import 'package:gymbroz/features/auth/domain/enitities/app_user.dart';
import 'package:gymbroz/features/auth/domain/repositories/auth_repo.dart';
import 'package:gymbroz/features/auth/presentation/bloc/auth_state.dart';
import 'package:supabase_flutter/supabase_flutter.dart';

class AuthRepoMethods implements AuthRepo {
  final _supabase = Supabase.instance.client;
  @override
  Future<String> AuthSignUpWithApple() {
    // TODO: implement AuthSignUpWithApple
    throw UnimplementedError();
  }

  @override
  Future<String?> SignInWithEmail(String email, String password) async {
    try {
      await _supabase.auth.signInWithPassword(password: password, email: email);
      final currentUserEmail = _supabase.auth.currentUser?.email;
      return currentUserEmail;
    } catch (e) {
      throw Exception('Error with SignInWithEmail: ${e}');
    }
  }

  @override
  Future<void> SignOut() async {
    try {
      await _supabase.auth.signOut();
    } catch (e) {
      throw Exception('Error with SignOut: ${e}');
    }
  }

  @override
  Future<String?> SignUpWithEmail(String email, String password) async {
    try {
     final authResponse =  await _supabase.auth.signUp(password: password, email: email);
      return authResponse.user!.email;
    } catch (e) {
      throw Exception('Error with SignUpWithEmail: ${e}');
    }
  }

  @override
  Future<String?> SignUpWithGoogle() async {
    try {
      // final GoogleSignIn _googleSignIn = GoogleSignIn(
      //     clientId:
      //         "790000923454-tn5e209bfhc5ctpg09hhm9pjhqce439u.apps.googleusercontent.com");
      // const webClientId = 'my-web.apps.googleusercontent.com';

      /// TODO: update the iOS client ID with your own.
      ///
      /// iOS Client ID that you registered with Google Cloud.
      const androidClientID =
          '219835938259-9gqu6vppo0tji5ga8m0bt4qhuf3ui8c4.apps.googleusercontent.com';

      // Google sign in on Android will work without providing the Android
      // Client ID registered on Google Cloud.

      const List<String> scopes = <String>[
        'email',
        'https://www.googleapis.com/auth/contacts.readonly',
      ];

      GoogleSignIn _googleSignIn = GoogleSignIn();
      final user = await _googleSignIn.signIn();
      return _googleSignIn.currentUser!.email;
    } catch (e) {
      throw Exception('Error with SignUpWithGoogle: ${e}');
    }
  }

  @override
  Future<String?> GetCurrentUser() async {
    try {
      final currentUserEmail = await _supabase.auth.currentUser!.email;
      return currentUserEmail;
    } catch (e) {
      throw Exception('Error with GetCurrentUser: ${e.toString()}');
    }
  }
}
