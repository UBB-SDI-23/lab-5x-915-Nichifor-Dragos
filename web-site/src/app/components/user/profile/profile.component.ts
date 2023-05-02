import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { UserProfile } from 'src/app/core/model/user.model';
import { StorageService } from 'src/app/core/service/storage.service';
import { UserService } from 'src/app/core/service/user.service';
import { User } from 'src/app/core/model/user.model';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  currentUser?: User;
  userProfile?: UserProfile;
  usernameActivatedRoute?: string;

  numberOfCars?: Number;
  numberOfPilots?: Number;
  numberOfRaces?: Number;

  loggedIn = true;

  constructor(
    private storageService: StorageService,
    private userService: UserService,
    private toastrService: ToastrService,
    private activatedRoute: ActivatedRoute,
    private router: Router
    ) { }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.usernameActivatedRoute = params['username']
      if (this.usernameActivatedRoute) {
        this.userService.getUserByUsername(this.usernameActivatedRoute).subscribe(
          (response) => { this.currentUser = response; this.getStatistics();},
          (error) => this.toastrService.error("Something went wrong", '', { progressBar: true }))
        this.userService.getUserProfileByUsername(this.usernameActivatedRoute).subscribe(
          (response) => { this.userProfile = response;},
          (error) => this.toastrService.error("Something went wrong", '', { progressBar: true }))
      }
      else {
        if (!this.storageService.isLoggedIn())
          this.loggedIn = false;
        else {
          this.currentUser = this.storageService.getUser();
          this.getStatistics();
          if (this.currentUser) {
            this.userService.getUserProfileById(this.currentUser.id).subscribe(
              (response) => { this.userProfile = response;},
              (error) => this.toastrService.error("Something went wrong", '', { progressBar: true }))
            }
        }
      }
    });
  }

  getStatistics() {
    if (this.currentUser) {
      this.userService.getNumberOfCars(this.currentUser.id).subscribe(
        (response) => { this.numberOfCars = response;},
        (error) => this.toastrService.error("Something went wrong", '', { progressBar: true }))
      this.userService.getNumberOfPilots(this.currentUser.id).subscribe(
        (response) => { this.numberOfPilots = response;},
        (error) => this.toastrService.error("Something went wrong", '', { progressBar: true }))
      this.userService.getNumberOfRaces(this.currentUser.id).subscribe(
        (response) => { this.numberOfRaces = response;},
        (error) => this.toastrService.error("Something went wrong", '', { progressBar: true }))
      }
      
  }

  isUserMale() {
    if (this.userProfile && this.userProfile.gender == 'male')
      return true;
    return false;
  }

  isProfileEditable() {
    if (this.storageService.getUser().username == this.currentUser?.username)
      return true;
    return false;
  }

  navigateToProfileEdit() {
    this.router.navigate(['/profile-edit'])
  }

}