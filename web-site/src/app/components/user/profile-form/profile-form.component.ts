import { Component, OnInit } from '@angular/core';

import { User, UserProfile } from 'src/app/core/model/user.model';

import { StorageService } from 'src/app/core/service/storage.service';
import { UserService } from 'src/app/core/service/user.service';

import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-profile-form',
  templateUrl: './profile-form.component.html',
  styleUrls: ['./profile-form.component.css']
})
export class ProfileFormComponent implements OnInit {

  currentUser?: User;
  userProfile?: UserProfile;
  usernameActivatedRoute?: string;

  numberOfCars?: Number;
  numberOfPilots?: Number;
  numberOfRaces?: Number;

  submitted = false;

  genders = ['male', 'female']
  maritalStatuses = ['single', 'married', 'divorced', 'separated', 'civil union', 'domestic partnership']

  constructor(
    private storageService: StorageService,
    private userService: UserService,
    private toastrService: ToastrService,
    private router: Router
    ) { }

  ngOnInit(): void {
    if (!this.storageService.isLoggedIn()){
      this.toastrService.error("Logging in is required", '', { progressBar: true }); this.onBackToHomePage() 
    }
    else {
      this.currentUser = this.storageService.getUser();
      if (this.currentUser) {
        this.userService.getUserProfileById(this.currentUser.id).subscribe(
          (response) => { this.userProfile = response;},
          (error) => this.toastrService.error("Something went wrong", '', { progressBar: true }))
        }
    }
  }

  onSubmit() { 
    this.submitted = true; 
    console.log("Reach")
    if (this.userProfile) {
      this.userService.updateUserProfile(this.userProfile, this.userProfile.id).subscribe(
        (response) => { this.toastrService.success("Profile updated successfully", '', { progressBar: true }); this.onBackToProfilePage()},
        (error) => { this.toastrService.error("Could not update profile", '', { progressBar: true }); this.onBackToProfilePage() });
    }
  }

  onBackToProfilePage() {
    this.router.navigate(['/profile'])
  }

  onBackToHomePage() {
    this.router.navigate(['/home-page'])
  }

}
