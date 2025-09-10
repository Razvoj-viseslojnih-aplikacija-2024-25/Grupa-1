import { Component, OnInit } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { Artikl } from '../../../models/artikl';
import { ArtiklService } from '../../../services/artikl-service';
import { MatDialog } from '@angular/material/dialog';
import { ArtiklDialog } from '../../dialogs/artikl-dialog/artikl-dialog';

@Component({
  selector: 'app-artikl-component',
  imports: [MatTableModule, MatIconModule, MatToolbarModule],
  templateUrl: './artikl-component.html',
  styleUrl: './artikl-component.css'
})
export class ArtiklComponent implements OnInit{
 
  displayedColumns = ['id','naziv','proizvodjac','actions'];
  dataSource !: MatTableDataSource<Artikl>;
  
  constructor(private artiklService:ArtiklService, private dialog: MatDialog){}

   ngOnInit(): void {
    this.loadData();
  }

  public loadData():void{
    this.artiklService.getAllArtikls().subscribe({
      next: data => {
        console.log(data);
        this.dataSource = new MatTableDataSource<Artikl>(data);
      },
      error: error => {
        console.log(error.message);
      }
    })
  }

  public openDialog(flag:number, id?:number, naziv?:String, proizvodjac?:String):void{
    const ref = this.dialog.open(ArtiklDialog, {data: {id,naziv,proizvodjac}});
    ref.componentInstance.flag = flag;
    ref.afterClosed().subscribe(
      response => {
        if(response === 1){
          this.ngOnInit();
        }
      }
    )
  }
}
