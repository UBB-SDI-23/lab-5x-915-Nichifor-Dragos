import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { StorageService } from 'src/app/core/service/storage.service';
import { AuthService } from 'src/app/core/service/authentication.service';
import { ToastrService } from 'ngx-toastr';
import { NavbarService } from 'src/app/core/service/navbar.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  constructor(
      private router: Router,
      private storageService: StorageService,
      private authService: AuthService,
      private toastrService: ToastrService,
      private navbarService: NavbarService
  ) {}

  private roles: string[] = [];
  isLoggedIn = false;
  showAdminBoard = false;
  username?: string;


  ngOnInit(): void {
    this.initialiseNavbar();

    this.navbarService.getLoginObservable().subscribe(() => {
      this.isLoggedIn = true;
      this.initialiseNavbar();
    });

    this.navbarService.getLogoutObservable().subscribe(() => {
      this.isLoggedIn = false;
      this.initialiseNavbar();
    });
  }

  initialiseNavbar(): void {
    this.isLoggedIn = this.storageService.isLoggedIn();

    if (this.isLoggedIn) {
      const user = this.storageService.getUser();
      this.roles = user.roles;
      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
      this.username = user.username;
    } 
  }

  logout(): void {
    this.authService.logout().subscribe({
      next: res => {
        this.storageService.clean();
        this.isLoggedIn = false;
        // window.location.reload();
      },
      error: err => {
        this.toastrService.error("Could not log out", '', { progressBar: true })
      },
      complete: () => {
        this.navbarService.logout();
      }
    });
  }

  getHeaderStyle() {
    if (this.router.url == '/home-page')
      return 'main-page-nav';
    else
      return 'bg-dark';
  }

  getLinkStyle() {
    if (this.router.url == '/home-page')
      return 'main-page-link';
    else
      return '';
  }

  getDropdownStyle() {
    if (this.router.url == '/home-page')
      return 'main-dropdown-menu';
    else
      return '';
  }

}
