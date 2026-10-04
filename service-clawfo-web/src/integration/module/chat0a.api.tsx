

export interface ClawfoChatRequest {
    chatId?: string|null|undefined;
    username?:  string|null|undefined;
}

export interface ClawfoMessageRequest {
    id: string;
    type: 'user' | 'clawfo' | 'karyawan';
    name: string;
    message: string;
    time: string;
}

export interface ClawfoMessageResponse extends ClawfoMessageRequest{

}

