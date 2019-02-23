import { Component } from '@angular/core';
import { ActionSheetController, AlertController, ModalController } from '@ionic/angular';
import { UserServiceService } from '../services/user-service.service';
import { ModalPage } from '../modal/modal.page';

@Component({
  selector: 'app-tab2',
  templateUrl: 'tab2.page.html',
  styleUrls: ['tab2.page.scss']
})
export class Tab2Page {

  users: any[] = [];

  constructor(
    public actionSheetController: ActionSheetController,
    public alertController: AlertController,
    public userService: UserServiceService,
    private modalController: ModalController
  ) { }

  ngOnInit() {
    this.userService.getUsers().subscribe((data) => { // Success
      this.users = data['results'];
      console.log(this.users);
    },
      (error) => { console.error(error); }
    )
  }

  async openModal(user){
    console.log(user);
    const modal = await this.modalController.create({
      component: ModalPage,
      componentProps:{
        user : user
      }
    });
    modal.present();
  }

}
