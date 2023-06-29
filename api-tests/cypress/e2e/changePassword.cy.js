describe('creating admins', () => {
  beforeEach(() => {
    cy.deleteUsers();
    cy.createTestAdmin();
  })
  it('can create an admin', () => {
    cy.login().then(({ body }) => {
      const token = body.token;
      cy.request({
        url: '/auth/editPassword',
        method: 'patch',
        body: {
          newPassword: 'Password42',
          confirmationNewPassword: 'Password42'
        },
        auth: {
          bearer: token
        },

      }).then(({ body }) => {
        expect(body.result).to.eq('Password changed')
        cy.request({
          url: '/auth/login',
          method: 'post',
          auth: {
            user: 'admin',
            pass: 'Password42'
          },
        }).then(({ body }) => {
          expect(body.user.username).to.eq('admin');
        })
      })
    })
  })
})