import { Component, OnInit } from '@angular/core';
import autoBind from 'auto-bind';
import {HallService} from "../../services/hall.service";
import { Router } from '@angular/router';
import Hall from '../../models/hall';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-hallen-lijst',
  templateUrl: './hallen-lijst.component.html',
  styleUrls: ['./hallen-lijst.component.css']
})
export class HallenLijstComponent implements OnInit {
  selectedLang: string = "";


  constructor(private hallService:HallService, private router:Router, private translateService: TranslateService) {
    autoBind(this);
    this.translateService.onLangChange.subscribe(function(){
      this.selectedLang = this.translateService.currentLang;
    }.bind(this));
    this.selectedLang = this.translateService.currentLang;
  }

  async ngOnInit() {
    console.log("init");
    try {
      await this.hallService.getHallen();
    }
    catch(err){
      console.log(err);
    }
  }

  boekZaal(hall:Hall){
    console.log("Boek zaal");
    this.router.navigate(['/room-planning', hall.getId()]);
  }
}
