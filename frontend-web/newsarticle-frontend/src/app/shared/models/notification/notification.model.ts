
export interface Notification {
    id?: number;
    newsArticleId: number;
    receiverUsernameWriter: string;
    senderUsernameEditor: string;
    approvedOrDisapproved: string;
    remark: string;
    creationDate: Date;
}