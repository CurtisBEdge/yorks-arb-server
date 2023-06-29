// ***********************************************
// This example commands.js shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************
//
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



Cypress.Commands.add('deleteUsers', () => {
    cy.request({
        url: '/test/users',
        method: 'delete'
    }).then(({ body }) => {
        expect(body.result).to.eq('Done');
    });
});

Cypress.Commands.add('createTestAdmin', () => {
    cy.request({
        url: `/test/create-admin`,
        method: 'post'
    }).then(({ body }) => {
        expect(body.result).to.eq('Done');
    });
});

Cypress.Commands.add('login', () => {
  cy.request({
    url: '/auth/login',
    method: 'post',
    auth: {
      user: 'admin',
      pass: 'Password1'
    },
  })
})


Cypress.Commands.add('deleteSigns', () => {
  cy.request({
    url: '/test/signs',
    method: 'delete'
  }).then(({body}) => {
    expect(body.result).to.eq('Done');
  });
});

Cypress.Commands.add('createSignwithImg', (token) => {
  return cy.fixture("tree.png", 'binary')
    .then((file) => Cypress.Blob.binaryStringToBlob(file))
    .then((blob) => {
      const formData = new FormData();
      formData.append("signImage", blob, "tree.png");
      formData.append("title", "tree");
      formData.append("description", "x");
      formData.append("lat", 1.0);
      formData.append("lon", 1.0);
      cy.request({
        url: "/signs",
        method: "POST",
        headers: {
          Authorization: `bearer ${token}`,
          'content-type': 'multipart/form-data'
        },
        body: formData
      })
    })
})

Cypress.Commands.add('createSignwithoutImg', (token) => {
  const formData = new FormData();
  const emptyBlob = new Blob([])
  formData.append("signImage", emptyBlob,"");
  formData.append("title", "tree");
  formData.append("description", "x");
  formData.append("lat", 1.0);
  formData.append("lon", 1.0);
  return cy.request({
    url: "/signs",
    method: "POST",
    headers: {
      Authorization: `bearer ${token}`,
    },
    body: formData
  })
})

Cypress.Commands.add('createTestSign', () => cy.request({
  url: `/test/create-sign`,
  method: 'post'
}));

