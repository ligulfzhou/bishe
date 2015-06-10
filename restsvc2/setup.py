from setuptools import setup, find_packages


setup(name='tornado_rest_api',
      version='0.1',
      description='tornado_rest_api',
      long_description='',
      author='Ligulf Zhou',
      author_email='ligulfzhou53@gmail.com',
      url='',
      include_package_data=True,
      classifiers=[],
      packages=find_packages(exclude=['tests']),
      install_requires=[
            'psycopg2',
            'tornado==4.2'
      ],
      tests_require=[
          'psycopg2',
          'pytest>=2.6.0',
          'pytest-pep8',
          'pytest-cov',
          'tox'
      ])