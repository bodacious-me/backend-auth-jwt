class AppUser {
  final String firstName;
  final String lastName;
  final String userName;
  final String email;
  final String age;
  final String gender;
  final String weight;
  final String height;
  final String? bio;

  AppUser(this.age, this.bio, this.email, this.firstName, this.gender,
      this.height, this.lastName, this.userName, this.weight);
}
