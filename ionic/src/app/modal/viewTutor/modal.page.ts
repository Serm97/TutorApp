import { Component, OnInit } from '@angular/core';
import { NavParams, ModalController } from '@ionic/angular';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.page.html',
  styleUrls: ['./modal.page.scss'],
})
export class ModalPage implements OnInit {

  user: any = null;
  constructor(private navParams: NavParams, private modalController: ModalController) { }

  ngOnInit() {
    this.user = this.navParams.get('user');
  }

  closeModal(){
    this.modalController.dismiss();
  }
}
