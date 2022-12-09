/// <reference types="cypress" />
// ***********************************************
// This example commands.ts shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************

Cypress.Commands.add('loginByGoogleApi', () => {
    cy.log('Logging in to Google');

    cy.request({
        method: 'POST',
        url: 'https://accounts.google.com/o/oauth2/token',
        body: {
            client_id: '550694368173-o54bo9j60mtfekrpqrkbr1saam1pg421.apps.googleusercontent.com',
            client_secret: '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',
            grant_type: 'refresh_token',
            redirect_uri: 'http://localhost:8080/',
            access_type: 'offline',
            refresh_token: '1//0cDwEQaSw-5RDCgYIARAAGAwSNwF-L9IrTUJEBVZ2SG8XQTLTUjaxusb6gTKra2skzrliS3rMBI5PMcZye4Jqq7WgHKpj_5zp7AY'
        }
    }).then(({ body }) => {
        console.log(body);
        const { id_token } = body;
        cy.request({
            method: 'GET',
            url: 'http://localhost:8080/api/verifyTokenBearer',
            headers: { Authorization: `Bearer ${id_token}` }
        }).then(_ => cy.visit('/'));
    });
});

//
// -- This is a parent command --
// Cypress.Commands.add('login', (email, password) => { ... })
//
//
// -- This is a child command --
// Cypress.Commands.add('drag', { prevSubject: 'element'}, (subject, options) => { ... })
//
//
// -- This is a dual command --
// Cypress.Commands.add('dismiss', { prevSubject: 'optional'}, (subject, options) => { ... })
//
//
// -- This will overwrite an existing command --
// Cypress.Commands.overwrite('visit', (originalFn, url, options) => { ... })
//
// declare global {
//   namespace Cypress {
//     interface Chainable {
//       login(email: string, password: string): Chainable<void>
//       drag(subject: string, options?: Partial<TypeOptions>): Chainable<Element>
//       dismiss(subject: string, options?: Partial<TypeOptions>): Chainable<Element>
//       visit(originalFn: CommandOriginalFn, url: string, options: Partial<VisitOptions>): Chainable<Element>
//     }
//   }
// }