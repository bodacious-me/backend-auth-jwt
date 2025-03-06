abstract class AuthEvent {}

class AuthSignUpWithEmailEvent extends AuthEvent {
  final String email;
  final String password;
  AuthSignUpWithEmailEvent(this.email, this.password);
}

class AuthSignUpWithGoogleEvent extends AuthEvent {}

class AuthSignUpWithAppleEvent extends AuthEvent {}

class AuthSignInWithEmailEvent extends AuthEvent {
  final String email;
  final String password;
  AuthSignInWithEmailEvent(this.email, this.password);
}

class AuthSignOutEvent extends AuthEvent {}

// class AuthCUrrentUserEvent {}