import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  constructor(
      private router: Router
  ) {}

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
