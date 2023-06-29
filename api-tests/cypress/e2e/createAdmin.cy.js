describe('creating admins', () => {
  beforeEach(() => {
    cy.deleteUsers();
    cy.createTestAdmin();
  })
  it('can create an admin', () => {
    cy.request({
      url: '/auth/login',
      method: 'post',
      auth: {
        user: 'admin',
        pass: 'Password1'
      },
    }).then(({ body }) => {
      const token = body.token;
      cy.request({
        url: '/auth/signup',
        method: 'post',
        body: {
          username: 'user',
          password: 'Password2',
          confirmationPassword: 'Password2'
        },
        auth: {
          bearer: token
        }
      }).then(({ body }) => {
        expect(body.result).to.eq('New user created successfully');
      });
    })
  })
})