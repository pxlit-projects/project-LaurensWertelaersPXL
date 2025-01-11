export interface Review {
    id?: number;
    newsArticleId: number;
    userNameEditor: string;
    remark: string;
    reviewDate: Date;
}