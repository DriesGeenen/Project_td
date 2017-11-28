import { Component, OnInit } from '@angular/core';
import autoBind from 'auto-bind';
import {HallService} from "../../services/hall.service";
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-add-hall',
  templateUrl: './add-hall.component.html',
  styleUrls: ['./add-hall.component.css']
})
export class AddHallComponent implements OnInit {


  constructor(private hallService:HallService) {
    autoBind(this);
  }

  async ngOnInit() {
    try {
      this.hallService.getHallen();
    }
    catch(err){
      console.log(err);
    }
  }

  addHall(){

  }
}
