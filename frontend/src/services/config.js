export function getHostNameServer() {
    const hostServer = 'localhost';
    const port = 8080;
    const url = `http://${hostServer}:${port}/api`;
    return url;
};