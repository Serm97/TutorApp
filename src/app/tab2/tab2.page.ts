import { Component } from '@angular/core';
import { ActionSheetController } from '@ionic/angular';

@Component({
  selector: 'app-tab2',
  templateUrl: 'tab2.page.html',
  styleUrls: ['tab2.page.scss']
})
export class Tab2Page {

  categorias: Array<any>;

  constructor(public actionSheetController: ActionSheetController) { 
    this.categorias = [
      {
        titulo:'Educación',
        color:"primary",
        icono:"book"
      },
      {
        titulo:'Cocina',
        color:"secondary",
        icono:"restaurant"
      },
      {
        titulo:'Estado fisico',
        color:"tertiary",
        icono:"bicycle"
      },
      {
        titulo:'Otros',
        color:"success",
        icono:"pricetag"
      }      
    ];
  }

 

  async presentActionSheet() {
    const actionSheet = await this.actionSheetController.create({
      header: 'Albums',
      buttons: [{
        text: 'Delete',
        role: 'destructive',
        icon: 'trash',
        handler: () => {
          console.log('Delete clicked');
        }
      }, {
        text: 'Share',
        icon: 'share',
        handler: () => {
          console.log('Share clicked');
        }
      }, {
        text: 'Play (open modal)',
        icon: 'arrow-dropright-circle',
        handler: () => {
          console.log('Play clicked');
        }
      }, {
        text: 'Favorite',
        icon: 'heart',
        handler: () => {
          console.log('Favorite clicked');
        }
      }, {
        text: 'Cancel',
        icon: 'close',
        role: 'cancel',
        handler: () => {
          console.log('Cancel clicked');
        }
      }]
    });
    await actionSheet.present();
  }

  async hola() {
    console.log('Hola Mundooo');
  }
}
