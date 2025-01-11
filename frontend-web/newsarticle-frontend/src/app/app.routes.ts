import { Routes } from '@angular/router';
import { LoginComponent } from './core/login/login.component';
import { WriterComponent } from './core/writer/writer.component';
import { EditorComponent } from './core/editor/editor.component';
import { MemberComponent } from './core/member/member.component';
import { AuthGuard } from './shared/routeguards/auth.guard';

export const routes: Routes = [
    { path: '', component: LoginComponent },
    { path: 'writer', component: WriterComponent, canActivate: [AuthGuard] },
    { path: 'editor', component: EditorComponent, canActivate: [AuthGuard] },
    { path: 'member', component: MemberComponent, canActivate: [AuthGuard] },
    { path: '**', redirectTo: '' } // Fallback voor onbekende routes
];
