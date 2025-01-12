export interface Comment {
    id?: number;
    newsArticleId: number;
    usernameCommenter: string;
    creationDate: Date;
    content: string;
}