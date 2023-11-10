use java14_team2;
# table syllabus
insert into syllabus (duration, version, id, `code`, `level`, `name`, `status`, course_objective, principle, technical_req) value (12, 1.0, 1, 'NPL', 1, 'C# Progaming Language', 'INACTIVE', '', '', 'Microsoft SQL Server 2005 Express, Microsoft Visual Studio 2017, Microsoft Office 2007 (Visio, Word, PowerPoint)');
insert into syllabus (duration, version, id, `code`, `level`, `name`, `status`, course_objective, principle, technical_req)value (7, 1.0, 2, 'CBG', 1, 'C# Basic Progaming', 'ACTIVE', '', '', 'Microsoft SQL Server 2005 Express, Microsoft Visual Studio 2017, Microsoft Office 2007 (Visio, Word, PowerPoint)');
insert into syllabus (duration, version, id, `code`, `level`, `name`, `status`, course_objective, principle, technical_req)value (5, 1.0, 3, 'NET', 1, '.NET Basic Progaming ', 'INACTIVE', '', '', 'Microsoft SQL Server 2005 Express, Microsoft Visual Studio 2017, Microsoft Office 2007 (Visio, Word, PowerPoint)');
insert into syllabus (duration, version, id, `code`, `level`, `name`, `status`, course_objective, principle, technical_req)value (15, 1.0, 4, 'PYT', 1, 'Python Basic Progaming', 'INACTIVE', '', '', 'Visual Studio Code, Microsoft Office 2007 (Visio, Word, PowerPoint)');
insert into syllabus (duration, version, id, `code`, `level`, `name`, `status`, course_objective, principle, technical_req)value (25, 1.0, 5, 'DOF', 1, 'DevOps Foundation', 'DRAFT', '', '', 'Monitoring với Prometheus, Grafana, Microsoft Office 2007 (Visio, Word, PowerPoint),');
insert into syllabus (duration, version, id, `code`, `level`, `name`, `status`, course_objective, principle, technical_req)value (25, 1.0, 6, 'AZD', 1, 'Azure DevOps Foundation', 'DRAFT', '', '', 'Monitoring với Prometheus, Grafana, Microsoft Office 2007 (Visio, Word, PowerPoint)');
insert into syllabus (duration, version, id, `code`, `level`, `name`, `status`, course_objective, principle, technical_req)value (25, 1.0, 7, 'FULJ', 1, 'Fullstack Java Web Developer', 'DRAFT', '', '', 'Java SE, Mysql Server , Microsoft Office 2007 (Visio, Word, PowerPoint)');
insert into syllabus (duration, version, id, `code`, `level`, `name`, `status`, course_objective, principle, technical_req)value (25, 1.0, 8, 'FULN', 1, 'Fullstack .NET Web Developer', 'INACTIVE', '', '', 'Microsoft SQL Server 2005 Express, Microsoft Visual Studio 2017, Mysql Server , Microsoft Office 2007 (Visio, Word, PowerPoint)');
insert into syllabus (duration, version, id, `code`, `level`, `name`, `status`, course_objective, principle, technical_req)value (25, 1.0, 9, 'TES', 1, 'ISTQB Foundation', 'INACTIVE', '', '', ' Microsoft Office 2007 (Visio, Word, PowerPoint)');

# table training_unit

insert into training_unit (id, syllabus_id, `day`) value (1, 1, 3);
insert into training_unit (id, syllabus_id, `day`) value (2, 1, 4);
insert into training_unit (id, syllabus_id, `day`) value (3, 3, 6);
insert into training_unit (id, syllabus_id, `day`) value (4, 4, 5);
insert into training_unit (id, syllabus_id, `day`) value (5, 5, 6);
insert into training_unit (id, syllabus_id, `day`) value (6, 6, 8);
insert into training_unit (id, syllabus_id, `day`) value (7, 7, 7);
insert into training_unit (id, syllabus_id, `day`) value (8, 8, 6);
insert into training_unit (id, syllabus_id, `day`) value (9, 9, 7);

# table output_standard
insert into output_standard (id, `name`, `description`) value (1, 'H1SD', '');
insert into output_standard (id, `name`, `description`) value (2, 'H2SD', '');
insert into output_standard (id, `name`, `description`) value (3, 'H4SD', '');
insert into output_standard (id, `name`, `description`) value (4, 'H6SD', '');
insert into output_standard (id, `name`, `description`) value (5, 'K3SD', '');
insert into output_standard (id, `name`, `description`) value (6, 'K5SD', '');
insert into output_standard (id, `name`, `description`) value (7, 'K6SD', '');
insert into output_standard (id, `name`, `description`) value (8, 'KT4D', '');

# table training_content

insert into training_content (id, output_standard_id, unit_id, name_content, duration, delivery_type) value(1,1,1,'', 120,'ASSIMENT_LAB');
insert into training_content (id, output_standard_id, unit_id, name_content, duration, delivery_type) value(2,1,1,'', 30,'ASSIMENT_LAB');
insert into training_content (id, output_standard_id, unit_id, name_content, duration, delivery_type) value(3,1,2,'', 30,'CONCEPT_LECTURE');
insert into training_content (id, output_standard_id, unit_id, name_content, duration, delivery_type) value(4,1,2,'', 30,'CONCEPT_LECTURE');
insert into training_content (id, output_standard_id, unit_id, name_content, duration, delivery_type) value(5,1,2,'', 30,'CONCEPT_LECTURE');
insert into training_content (id, output_standard_id, unit_id, name_content, duration, delivery_type) value(6,1,2,'', 120,'ASSIMENT_LAB');
insert into training_content (id, output_standard_id, unit_id, name_content, duration, delivery_type) value(7,2,2,'', 45,'TEST_QUIZ');

# table syllabus-standard

insert into `syllabus-standard` (id, standard_id, syllabus_id) value(1, 1, 3);
insert into `syllabus-standard` (id, standard_id, syllabus_id) value(2, 1, 7);
insert into `syllabus-standard` (id, standard_id, syllabus_id) value(3, 2, 3);
insert into `syllabus-standard` (id, standard_id, syllabus_id) value(4, 2, 4);
insert into `syllabus-standard` (id, standard_id, syllabus_id) value(5, 2, 7);
insert into `syllabus-standard` (id, standard_id, syllabus_id) value(6, 3, 6);
insert into `syllabus-standard` (id, standard_id, syllabus_id) value(7, 3, 8);
insert into `syllabus-standard` (id, standard_id, syllabus_id) value(8, 4, 8);
insert into `syllabus-standard` (id, standard_id, syllabus_id) value(9, 4, 3);
insert into `syllabus-standard` (id, standard_id, syllabus_id) value(10, 5, 4);




