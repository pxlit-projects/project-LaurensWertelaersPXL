import { Routes } from '@angular/router';
import { LoginComponent } from './core/login/login.component';
import { WriterComponent } from './core/writer/writer.component';
import { EditorComponent } from './core/editor/editor.component';
import { MemberComponent } from './core/member/member.component';

export const routes: Routes = [
    { path: '', component: LoginComponent },
    { path: 'writer', component: WriterComponent },
    { path: 'editor', component: EditorComponent },
    { path: 'member', component: MemberComponent },
    { path: '**', redirectTo: '' } // Fallback voor onbekende routes
];
